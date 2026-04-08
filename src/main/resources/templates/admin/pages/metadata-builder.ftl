<#import "../layout/base.ftl" as layout>

<@layout.layout>

<div class="row">

    <div class="col-4">
        <div class="card p-3">
            <h5>Fields</h5>
            <div id="fields"></div>
        </div>
    </div>

    <div class="col-8">
        <div class="card p-3">
            <h5>Canvas</h5>
            <div id="canvas" style="height:400px;border:1px dashed #334155;"></div>
        </div>
    </div>

</div>

<script>
async function load() {
    try {
        const res = await axios.get("/api/v1/platform/fields");
        let html = "";

        if (res.data && res.data.data) {
            res.data.data.forEach(function(f) {
                html += '<div draggable="true" class="p-2 bg-dark mb-2">'
                     +  (f.fieldName ?? "")
                     +  '</div>';
            });
        }

        document.getElementById("fields").innerHTML = html;
    } catch (e) {
        console.error(e);
    }
}
load();
</script>

</@layout.layout>