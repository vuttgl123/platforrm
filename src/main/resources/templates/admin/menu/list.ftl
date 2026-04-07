<div class="card">
    <div class="card-body">
        <div class="section-header mb-3">
            <div>
                <h5 class="mb-1">Menu Management</h5>
                <p class="text-muted mb-0">Configure navigation tree for platform modules</p>
            </div>
            <a href="/admin/menus/create" class="btn btn-primary">Create Menu</a>
        </div>

        <div class="filter-bar">
            <input type="text" class="form-control" placeholder="Search menu code or label">
            <select class="form-select">
                <option>All modules</option>
                <option>core</option>
                <option>crm</option>
            </select>
            <select class="form-select">
                <option>All status</option>
                <option>Active</option>
                <option>Inactive</option>
            </select>
            <button class="btn btn-outline-secondary">Filter</button>
        </div>

        <div class="table-responsive mt-3">
            <table class="table table-hover align-middle admin-table">
                <thead>
                <tr>
                    <th>Code</th>
                    <th>Label</th>
                    <th>Module</th>
                    <th>Path</th>
                    <th>Parent</th>
                    <th>Status</th>
                    <th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>dashboard</td>
                    <td>Dashboard</td>
                    <td>core</td>
                    <td>/admin</td>
                    <td>-</td>
                    <td><span class="badge bg-success">Active</span></td>
                    <td class="text-end">
                        <a href="#" class="btn btn-sm btn-outline-secondary">View</a>
                        <a href="#" class="btn btn-sm btn-outline-primary">Edit</a>
                    </td>
                </tr>
                <tr>
                    <td>crm_customers</td>
                    <td>Customers</td>
                    <td>crm</td>
                    <td>/admin/entities/10</td>
                    <td>crm_root</td>
                    <td><span class="badge bg-success">Active</span></td>
                    <td class="text-end">
                        <a href="#" class="btn btn-sm btn-outline-secondary">View</a>
                        <a href="#" class="btn btn-sm btn-outline-primary">Edit</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>