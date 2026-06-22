// =========================================
// ハンバーガーメニューとサイドメニューの開閉制御
// =========================================

document.addEventListener("DOMContentLoaded", function() {
    
    // HTMLから対象の要素を取得
    const hamburgerMenu = document.getElementById("hamburgerMenu");
    const sideMenu = document.getElementById("sideMenu");
    const sideMenuOverlay = document.getElementById("sideMenuOverlay");
    const closeMenuBtn = document.getElementById("closeMenuBtn");

    // メニューを開く処理
    function openMenu() {
        if (sideMenu && sideMenuOverlay) {
            sideMenu.classList.add("open");          // メニューを左からスライドイン
            sideMenuOverlay.classList.add("show");   // 背景の暗い幕をフェードイン
            document.body.style.overflow = "hidden"; // 後ろの画面がスクロールしないように固定
        }
    }

    // メニューを閉じる処理
    function closeMenu() {
        if (sideMenu && sideMenuOverlay) {
            sideMenu.classList.remove("open");       // メニューを左へスライドアウト
            sideMenuOverlay.classList.remove("show"); // 背景の暗い幕をフェードアウト
            document.body.style.overflow = "auto";   // 固定していたスクロールを解除
        }
    }

    // イベントリスナーの登録（要素が存在する画面でのみ動作させるためのif文）
    if (hamburgerMenu) {
        hamburgerMenu.addEventListener("click", openMenu);
    }
    
    if (closeMenuBtn) {
        closeMenuBtn.addEventListener("click", closeMenu);
    }
    
    if (sideMenuOverlay) {
        // メニュー外の暗い幕をクリックした時も閉じるようにする
        sideMenuOverlay.addEventListener("click", closeMenu);
    }
    
});