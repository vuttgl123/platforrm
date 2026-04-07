<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <div>
                <h5 class="mb-1">Module List</h5>
                <p class="text-muted mb-0">Manage dynamic modules</p>
            </div>
            <a href="/admin/modules/create" class="btn btn-primary">Create Module</a>
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Code</th>
                    <th>Name</th>
                    <th>Status</th>
                    <th>Created At</th>
                    <th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>core</td>
                    <td>Core Platform</td>
                    <td><span class="badge bg-success">Active</span></td>
                    <td>2026-04-01</td>
                    <td class="text-end">
                        <a href="/admin/modules/1" class="btn btn-sm btn-outline-secondary">View</a>
                        <a href="/admin/modules/1/edit" class="btn btn-sm btn-outline-primary">Edit</a>
                        <button class="btn btn-sm btn-outline-danger">Delete</button>
                    </td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>crm</td>
                    <td>CRM Module</td>
                    <td><span class="badge bg-success">Active</span></td>
                    <td>2026-04-01</td>
                    <td class="text-end">
                        <a href="/admin/modules/2" class="btn btn-sm btn-outline-secondary">View</a>
                        <a href="/admin/modules/2/edit" class="btn btn-sm btn-outline-primary">Edit</a>
                        <button class="btn btn-sm btn-outline-danger">Delete</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>