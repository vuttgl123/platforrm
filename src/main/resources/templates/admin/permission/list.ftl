<div class="card">
    <div class="card-body">
        <div class="section-header mb-3">
            <div>
                <h5 class="mb-1">Permission Matrix</h5>
                <p class="text-muted mb-0">Manage access by role, module and action</p>
            </div>
            <a href="/admin/permissions/create" class="btn btn-primary">Assign Permission</a>
        </div>

        <div class="filter-bar">
            <select class="form-select">
                <option>All roles</option>
                <option>ADMIN</option>
                <option>OPERATOR</option>
                <option>VIEWER</option>
            </select>
            <select class="form-select">
                <option>All modules</option>
                <option>core</option>
                <option>crm</option>
            </select>
            <button class="btn btn-outline-secondary">Apply</button>
        </div>

        <div class="table-responsive mt-3">
            <table class="table table-bordered align-middle permission-matrix">
                <thead>
                <tr>
                    <th>Role</th>
                    <th>Resource</th>
                    <th>View</th>
                    <th>Create</th>
                    <th>Edit</th>
                    <th>Delete</th>
                    <th>Approve</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>ADMIN</td>
                    <td>customer</td>
                    <td>✔</td>
                    <td>✔</td>
                    <td>✔</td>
                    <td>✔</td>
                    <td>✔</td>
                </tr>
                <tr>
                    <td>OPERATOR</td>
                    <td>customer</td>
                    <td>✔</td>
                    <td>✔</td>
                    <td>✔</td>
                    <td>✖</td>
                    <td>✖</td>
                </tr>
                <tr>
                    <td>VIEWER</td>
                    <td>customer</td>
                    <td>✔</td>
                    <td>✖</td>
                    <td>✖</td>
                    <td>✖</td>
                    <td>✖</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>