<div class="card">
    <div class="card-body">
        <h5 class="mb-3">Entity Detail</h5>

        <div class="row g-3">
            <div class="col-md-6">
                <label class="detail-label">Code</label>
                <div class="detail-value">${entity.code!'customer'}</div>
            </div>
            <div class="col-md-6">
                <label class="detail-label">Name</label>
                <div class="detail-value">${entity.name!'Customer'}</div>
            </div>
            <div class="col-md-6">
                <label class="detail-label">Module</label>
                <div class="detail-value">${entity.moduleCode!'crm'}</div>
            </div>
            <div class="col-md-6">
                <label class="detail-label">Status</label>
                <div class="detail-value">${entity.status!'PUBLISHED'}</div>
            </div>
            <div class="col-12">
                <label class="detail-label">Description</label>
                <div class="detail-value">${entity.description!'Customer master entity'}</div>
            </div>
        </div>

        <div class="mt-4 d-flex gap-2">
            <a href="/admin/entities/${entity.id!'10'}/edit" class="btn btn-primary">Edit</a>
            <a href="/admin/entities" class="btn btn-outline-secondary">Back</a>
        </div>
    </div>
</div>