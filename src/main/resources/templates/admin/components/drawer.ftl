<div id="drawer" style="position:fixed;right:-500px;top:0;width:500px;height:100%;background:#020617;transition:0.3s;">
    <div id="drawerContent"></div>
</div>

<script>
function openDrawer(html){
    document.getElementById("drawerContent").innerHTML=html;
    document.getElementById("drawer").style.right="0";
}
</script>