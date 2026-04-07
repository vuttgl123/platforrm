<div class="card">
    <div class="card-body">
        <h5 class="mb-3">Module Detail</h5>

        <div class="row g-3">
            <div class="col-md-6">
                <label class="detail-label">ID</label>
                <div class="detail-value">${module.id!'1'}</div>
            </div>

            <div class="col-md-6">
                <label class="detail-label">Code</label>
                <div class="detail-value">${module.code!'core'}</div>
            </div>

            <div class="col-md-6">
                <label class="detail-label">Name</label>
                <div class="detail-value">${module.name!'Core Platform'}</div>
            </div>

            <div class="col-md-6">
                <label class="detail-label">Status</label>
                <div class="detail-value">
                    <span class="badge bg-success">Active</span>
                </div>
            </div>

            <div class="col-12">
                <label class="detail-label">Description</label>
                <div class="detail-value">${module.description!'Core metadata module'}</div>
            </div>
        </div>

        <div class="mt-4 d-flex gap-2">
            <a href="/admin/modules/${module.id!'1'}/edit" class="btn btn-primary">Edit</a>
            <a href="/admin/modules" class="btn btn-outline-secondary">Back</a>
        </div>
    </div>
</div>