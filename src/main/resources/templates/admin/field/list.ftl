<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <div>
                <h5 class="mb-1">Field List</h5>
                <p class="text-muted mb-0">Manage field definitions</p>
            </div>
            <a href="/admin/fields/create" class="btn btn-primary">Create Field</a>
        </div>

        <table class="table table-hover align-middle">
            <thead>
            <tr>
                <th>ID</th>
                <th>Code</th>
                <th>Label</th>
                <th>Entity</th>
                <th>Type</th>
                <th>Required</th>
                <th class="text-end">Actions</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>101</td>
                <td>full_name</td>
                <td>Full Name</td>
                <td>customer</td>
                <td>TEXT</td>
                <td><span class="badge bg-danger">Yes</span></td>
                <td class="text-end">
                    <a href="/admin/fields/101" class="btn btn-sm btn-outline-secondary">View</a>
                    <a href="/admin/fields/101/edit" class="btn btn-sm btn-outline-primary">Edit</a>
                </td>
            </tr>
            <tr>
                <td>102</td>
                <td>email</td>
                <td>Email</td>
                <td>customer</td>
                <td>EMAIL</td>
                <td><span class="badge bg-danger">Yes</span></td>
                <td class="text-end">
                    <a href="/admin/fields/102" class="btn btn-sm btn-outline-secondary">View</a>
                    <a href="/admin/fields/102/edit" class="btn btn-sm btn-outline-primary">Edit</a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>