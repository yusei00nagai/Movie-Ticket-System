// =========================================
// 映画詳細画面用 JavaScript (イベントリスナー完全分離版)
// =========================================

document.addEventListener("DOMContentLoaded", function() {

    // -----------------------------------------
    // 1. エリアタブ（北海道・東北など）の切り替え処理
    // -----------------------------------------
    const areaTabs = document.querySelectorAll('.area-tab');
    areaTabs.forEach(tab => {
        tab.addEventListener('click', function() {
            // 全てのタブから 'active' を外す
            areaTabs.forEach(t => t.classList.remove('active'));
            // クリックされたタブに 'active' を付ける
            this.classList.add('active');

            // 全ての劇場リストコンテナを非表示にする
            const listContainers = document.querySelectorAll('.theater-list-container');
            listContainers.forEach(container => container.classList.remove('active'));

            // data-area-id 属性から対象のIDを取得し表示する
            const targetId = this.getAttribute('data-area-id');
            const targetContainer = document.getElementById(targetId);
            if (targetContainer) {
                targetContainer.classList.add('active');
            }
        });
    });


    // -----------------------------------------
    // 2. アコーディオン（カレンダー展開）処理
    // -----------------------------------------
    const theaterItems = document.querySelectorAll('.theater-item:not(.empty-message)');
    theaterItems.forEach(item => {
        item.addEventListener('click', function() {
            // openクラスを付け外しすることでCSSの max-height アニメーションを発動
            this.classList.toggle('open');
        });
    });


    // -----------------------------------------
    // 3. イベントバブリングの防止
    // -----------------------------------------
    // アコーディオンの中身（カレンダーや時間枠）をクリックした時に、
    // 親要素のクリックイベントが反応してアコーディオンが閉じてしまうのを防ぐ
    const accordionContents = document.querySelectorAll('.accordion-content');
    accordionContents.forEach(content => {
        content.addEventListener('click', function(event) {
            event.stopPropagation();
        });
    });


    // -----------------------------------------
    // 4. 日付タブの切り替え処理
    // -----------------------------------------
    const dateTabs = document.querySelectorAll('.date-tab');
    dateTabs.forEach(tab => {
        tab.addEventListener('click', function(event) {
            // イベント伝播防止（念のため）
            event.stopPropagation();

            // 同じul内の全タブからactiveを外す
            const tabList = this.closest('.date-tabs');
            const siblingTabs = tabList.querySelectorAll('.date-tab');
            siblingTabs.forEach(t => t.classList.remove('active'));
            
            // クリックされたタブを active にする
            this.classList.add('active');

            // 親のアコーディオン領域を取得し、全タイムテーブルパネルを隠す
            const accordionContent = this.closest('.accordion-content');
            const allPanels = accordionContent.querySelectorAll('.timetable-panel');
            allPanels.forEach(panel => panel.classList.remove('active'));

            // data-target-date 属性から対象のIDを取得し表示する
            const targetDateId = this.getAttribute('data-target-date');
            const targetPanel = accordionContent.querySelector('#' + targetDateId);
            if (targetPanel) {
                targetPanel.classList.add('active');
            }
        });
    });


    // -----------------------------------------
    // 5. 画像拡大モーダル処理
    // -----------------------------------------
    const subImages = document.querySelectorAll('.sub-img');
    const modal = document.getElementById("imageModal");
    const expandedImg = document.getElementById("expandedImg");
    const closeBtn = document.querySelector(".close-btn");

    // 画像クリックでモーダルを開く
    subImages.forEach(img => {
        img.addEventListener('click', function() {
            if (expandedImg && modal) {
                expandedImg.src = this.src;
                modal.style.display = "flex";
                document.body.style.overflow = "hidden"; // 背景スクロール停止
            }
        });
    });

    // モーダルの背景クリックで閉じる
    if (modal) {
        modal.addEventListener('click', function() {
            modal.style.display = "none";
            document.body.style.overflow = "auto";
        });
    }

    // 閉じる(×)ボタンクリックで閉じる
    if (closeBtn) {
        closeBtn.addEventListener('click', function() {
            if (modal) {
                modal.style.display = "none";
                document.body.style.overflow = "auto";
            }
        });
    }

    // 画像自体をクリックしたときは閉じないようにする
    if (expandedImg) {
        expandedImg.addEventListener('click', function(event) {
            event.stopPropagation();
        });
    }

    // -----------------------------------------
    // 6. ページトップへ戻るボタンの制御
    // -----------------------------------------
    const scrollTopBtn = document.getElementById('scrollTopBtn');
    
    // スクロール量に応じてボタンを表示/非表示
    window.addEventListener('scroll', function() {
        // 300px以上スクロールしたらボタンを表示
        if (window.scrollY > 300) {
            scrollTopBtn.classList.add('show');
        } else {
            scrollTopBtn.classList.remove('show');
        }
    });

    // ボタンクリックで滑らかに一番上へ戻る
    if (scrollTopBtn) {
        scrollTopBtn.addEventListener('click', function() {
            window.scrollTo({
                top: 0,
                behavior: 'smooth'
            });
        });
    }

});