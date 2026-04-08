<div class="sidebar">
    <h4 class="text-white mb-4">MetaForge</h4>

    <a href="/">Dashboard</a>
    <a href="/entities">Entities</a>
    <a href="/records">Records</a>
    <a href="/builder">Builder</a>

    <hr>

    <div id="dynamicMenu"></div>
</div>

<script>
async function loadMenu() {
    try {
        const res = await axios.get("/api/v1/platform/modules");
        let html = "";

        if (res.data && res.data.data && Array.isArray(res.data.data)) {
            res.data.data.forEach(function(m) {
                html += '<a href="/entities?module=' + (m.id ?? "") + '">'
                     +  (m.moduleName ?? "Unnamed Module")
                     +  '</a>';
            });
        }

        document.getElementById("dynamicMenu").innerHTML = html;
    } catch (e) {
        console.error("Menu load error", e);
        document.getElementById("dynamicMenu").innerHTML =
            '<div class="text-secondary small">Cannot load menu</div>';
    }
}

loadMenu();
</script>