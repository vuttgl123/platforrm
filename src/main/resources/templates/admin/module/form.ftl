<div class="card">
    <div class="card-body">
        <div class="row g-3">
            <div class="col-md-6">
                <label class="form-label">Code</label>
                <input type="text" class="form-control" name="code" value="${module.code!''}" placeholder="core_module">
            </div>

            <div class="col-md-6">
                <label class="form-label">Name</label>
                <input type="text" class="form-control" name="name" value="${module.name!''}" placeholder="Core Module">
            </div>

            <div class="col-12">
                <label class="form-label">Description</label>
                <textarea class="form-control" rows="4" name="description">${module.description!''}</textarea>
            </div>

            <div class="col-md-4">
                <label class="form-label">Status</label>
                <select class="form-select" name="active">
                    <option value="true" <#if (module.active!'true') == 'true'>selected</#if>>Active</option>
                    <option value="false" <#if (module.active!'true') == 'false'>selected</#if>>Inactive</option>
                </select>
            </div>
        </div>
    </div>
</div>