<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <div>
                <h5 class="mb-1">Entity List</h5>
                <p class="text-muted mb-0">Manage entity definitions</p>
            </div>
            <a href="/admin/entities/create" class="btn btn-primary">Create Entity</a>
        </div>

        <table class="table table-hover align-middle">
            <thead>
            <tr>
                <th>ID</th>
                <th>Code</th>
                <th>Name</th>
                <th>Module</th>
                <th>Status</th>
                <th class="text-end">Actions</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>10</td>
                <td>customer</td>
                <td>Customer</td>
                <td>crm</td>
                <td><span class="badge bg-success">Published</span></td>
                <td class="text-end">
                    <a href="/admin/entities/10" class="btn btn-sm btn-outline-secondary">View</a>
                    <a href="/admin/entities/10/edit" class="btn btn-sm btn-outline-primary">Edit</a>
                </td>
            </tr>
            <tr>
                <td>11</td>
                <td>lead</td>
                <td>Lead</td>
                <td>crm</td>
                <td><span class="badge bg-warning text-dark">Draft</span></td>
                <td class="text-end">
                    <a href="/admin/entities/11" class="btn btn-sm btn-outline-secondary">View</a>
                    <a href="/admin/entities/11/edit" class="btn btn-sm btn-outline-primary">Edit</a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>