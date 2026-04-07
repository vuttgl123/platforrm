<div class="card">
    <div class="card-body">
        <h5 class="mb-3">Field Detail</h5>

        <div class="row g-3">
            <div class="col-md-6">
                <label class="detail-label">Code</label>
                <div class="detail-value">${field.code!'full_name'}</div>
            </div>
            <div class="col-md-6">
                <label class="detail-label">Label</label>
                <div class="detail-value">${field.label!'Full Name'}</div>
            </div>
            <div class="col-md-6">
                <label class="detail-label">Entity</label>
                <div class="detail-value">${field.entityCode!'customer'}</div>
            </div>
            <div class="col-md-6">
                <label class="detail-label">Field Type</label>
                <div class="detail-value">${field.fieldType!'TEXT'}</div>
            </div>
            <div class="col-md-6">
                <label class="detail-label">Required</label>
                <div class="detail-value">${field.required!'true'}</div>
            </div>
            <div class="col-md-6">
                <label class="detail-label">Order No</label>
                <div class="detail-value">${field.orderNo!'1'}</div>
            </div>
            <div class="col-12">
                <label class="detail-label">Default Value</label>
                <div class="detail-value">${field.defaultValue!''}</div>
            </div>
        </div>

        <div class="mt-4 d-flex gap-2">
            <a href="/admin/fields/${field.id!'101'}/edit" class="btn btn-primary">Edit</a>
            <a href="/admin/fields" class="btn btn-outline-secondary">Back</a>
        </div>
    </div>
</div>