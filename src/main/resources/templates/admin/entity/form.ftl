<div class="card">
    <div class="card-body">
        <div class="row g-3">
            <div class="col-md-6">
                <label class="form-label">Code</label>
                <input type="text" class="form-control" name="code" value="${entity.code!''}">
            </div>

            <div class="col-md-6">
                <label class="form-label">Name</label>
                <input type="text" class="form-control" name="name" value="${entity.name!''}">
            </div>

            <div class="col-md-6">
                <label class="form-label">Module</label>
                <select class="form-select" name="moduleCode">
                    <option value="">Select module</option>
                    <option value="core">core</option>
                    <option value="crm">crm</option>
                </select>
            </div>

            <div class="col-md-6">
                <label class="form-label">Status</label>
                <select class="form-select" name="status">
                    <option value="DRAFT">Draft</option>
                    <option value="PUBLISHED">Published</option>
                </select>
            </div>

            <div class="col-12">
                <label class="form-label">Description</label>
                <textarea class="form-control" rows="4" name="description">${entity.description!''}</textarea>
            </div>
        </div>
    </div>
</div>