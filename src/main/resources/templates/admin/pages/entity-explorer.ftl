<#import "../layout/base.ftl" as layout>

<@layout.layout>

<div class="card p-3">

    <div class="d-flex justify-content-between mb-3">
        <input class="form-control w-25" placeholder="Search...">
        <button class="btn btn-primary" onclick="openDrawer()">+ New</button>
    </div>

    <table class="table table-dark table-hover">
        <thead id="thead"></thead>
        <tbody id="tbody"></tbody>
    </table>

</div>

<div id="drawer" style="position:fixed;right:-400px;top:0;width:400px;height:100%;background:#020617;transition:0.3s;padding:20px;">
    <h5>Create</h5>
    <input class="form-control mb-2">
    <button class="btn btn-primary">Save</button>
</div>

<script>
const entityId = 1;

function openDrawer() {
    document.getElementById("drawer").style.right = "0";
}

async function load() {
    try {
        const fields = await axios.get("/api/v1/platform/fields/by-entity/" + entityId);
        const records = await axios.get("/api/v1/runtime/records");

        let th = "";
        if (fields.data && fields.data.data) {
            fields.data.data.forEach(function(f) {
                th += "<th>" + (f.fieldName ?? "") + "</th>";
            });
        }
        document.getElementById("thead").innerHTML = th;

        let tb = "";
        if (records.data && records.data.data) {
            records.data.data.forEach(function(r) {
                tb += '<tr onclick="view(' + (r.id ?? 0) + ')">'
                   +  '<td>' + (r.displayName ?? "") + '</td>'
                   +  '</tr>';
            });
        }
        document.getElementById("tbody").innerHTML = tb;
    } catch (e) {
        console.error(e);
    }
}

function view(id) {
    location.href = "/records?id=" + id;
}

load();
</script>

</@layout.layout>