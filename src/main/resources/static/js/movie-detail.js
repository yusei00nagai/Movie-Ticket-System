// =========================================
// タブ切り替え処理（画面遷移なし・非同期なし）
// =========================================
function switchTab(areaId, clickedElement) {
    // 1. 全てのタブから 'active' クラスを取り除く
    const tabs = document.querySelectorAll('.area-tabs li');
    tabs.forEach(tab => tab.classList.remove('active'));

    // 2. クリックされたタブに 'active' クラスを付ける（白くなる）
    clickedElement.classList.add('active');

    // 3. 全ての劇場リストコンテナから 'active' クラスを取り除き、非表示にする
    const listContainers = document.querySelectorAll('.theater-list-container');
    listContainers.forEach(container => container.classList.remove('active'));

    // 4. 選択されたエリアのIDを持つコンテナに 'active' クラスを付け、表示する
    const targetContainer = document.getElementById(areaId);
    if (targetContainer) {
        targetContainer.classList.add('active');
    }
}

// =========================================
// 画像モーダル処理
// =========================================
function openModal(imageSrc) {
    const modal = document.getElementById("imageModal");
    const modalImg = document.getElementById("expandedImg");
    modalImg.src = imageSrc;
    modal.style.display = "flex";
    document.body.style.overflow = "hidden";
}

function closeModal() {
    const modal = document.getElementById("imageModal");
    modal.style.display = "none";
    document.body.style.overflow = "auto";
}