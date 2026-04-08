<#import "../layout/base.ftl" as layout>

<@layout.layout>

<div class="row g-3">

    <div class="col-3">
        <div class="card p-3">
            <h6>Total Entities</h6>
            <h3 id="entityCount">...</h3>
        </div>
    </div>

    <div class="col-3">
        <div class="card p-3">
            <h6>Total Records</h6>
            <h3 id="recordCount">...</h3>
        </div>
    </div>

</div>

<script>
axios.get("/api/v1/platform/entities/all")
.then(r=>document.getElementById("entityCount").innerText=r.data.data.length)
.catch(()=>{});

axios.get("/api/v1/runtime/records/all")
.then(r=>document.getElementById("recordCount").innerText=r.data.data.length)
.catch(()=>{});
</script>

</@layout.layout>