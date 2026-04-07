<div class="card">
    <div class="card-body">
        <div class="row g-3">
            <div class="col-md-6">
                <label class="form-label">Code</label>
                <input type="text" class="form-control" name="code" value="${field.code!''}">
            </div>

            <div class="col-md-6">
                <label class="form-label">Label</label>
                <input type="text" class="form-control" name="label" value="${field.label!''}">
            </div>

            <div class="col-md-6">
                <label class="form-label">Entity</label>
                <select class="form-select" name="entityCode">
                    <option value="">Select entity</option>
                    <option value="customer">customer</option>
                    <option value="lead">lead</option>
                </select>
            </div>

            <div class="col-md-6">
                <label class="form-label">Field Type</label>
                <select class="form-select" name="fieldType">
                    <option value="TEXT">TEXT</option>
                    <option value="NUMBER">NUMBER</option>
                    <option value="EMAIL">EMAIL</option>
                    <option value="DATE">DATE</option>
                    <option value="BOOLEAN">BOOLEAN</option>
                    <option value="SELECT">SELECT</option>
                </select>
            </div>

            <div class="col-md-4">
                <label class="form-label">Required</label>
                <select class="form-select" name="required">
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </select>
            </div>

            <div class="col-md-4">
                <label class="form-label">Sortable</label>
                <select class="form-select" name="sortable">
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </select>
            </div>

            <div class="col-md-4">
                <label class="form-label">Order No</label>
                <input type="number" class="form-control" name="orderNo" value="${field.orderNo!'0'}">
            </div>

            <div class="col-12">
                <label class="form-label">Default Value</label>
                <input type="text" class="form-control" name="defaultValue" value="${field.defaultValue!''}">
            </div>
        </div>
    </div>
</div>