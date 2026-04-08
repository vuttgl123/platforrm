<#import "../layout/base.ftl" as layout>

<@layout.layout>

<div class="card p-3">

    <div class="d-flex justify-content-between">
        <h4>Record Detail</h4>
        <button class="btn btn-success" onclick="loadTransitions()">Workflow</button>
    </div>

    <ul class="nav nav-tabs mt-3">
        <li class="nav-item"><a class="nav-link active" href="javascript:void(0)">Detail</a></li>
        <li class="nav-item"><a class="nav-link" href="javascript:void(0)">Relation</a></li>
        <li class="nav-item"><a class="nav-link" href="javascript:void(0)">History</a></li>
    </ul>

    <div id="form" class="mt-3"></div>

</div>

<script>
const id = new URLSearchParams(location.search).get("id");

async function load() {
    if (!id) {
        document.getElementById("form").innerHTML =
            '<div class="alert alert-warning">Missing record id</div>';
        return;
    }

    try {
        const res = await axios.get("/api/v1/runtime/record-values/by-record/" + id);
        let html = "";

        if (res.data && res.data.data) {
            res.data.data.forEach(function(f) {
                html += '<div class="mb-2">';
                html += '<label>' + (f.fieldName ?? "") + '</label>';
                html += '<input class="form-control" value="' + (f.value ?? "") + '">';
                html += '</div>';
            });
        }

        document.getElementById("form").innerHTML = html;
    } catch (e) {
        console.error(e);
    }
}

async function loadTransitions() {
    if (!id) return;
    const res = await axios.get("/api/v1/runtime/records/" + id + "/available-transitions");
    alert(JSON.stringify(res.data.data));
}

load();
</script>

</@layout.layout>