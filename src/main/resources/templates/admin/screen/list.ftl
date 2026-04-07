<div class="card">
    <div class="card-body">
        <div class="section-header mb-3">
            <div>
                <h5 class="mb-1">Screen Definitions</h5>
                <p class="text-muted mb-0">Manage UI metadata and dynamic screens</p>
            </div>
            <a href="/admin/screens/create" class="btn btn-primary">Create Screen</a>
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle admin-table">
                <thead>
                <tr>
                    <th>Code</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Entity</th>
                    <th>Layout</th>
                    <th>Status</th>
                    <th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>customer_list</td>
                    <td>Customer List</td>
                    <td>LIST</td>
                    <td>customer</td>
                    <td>TABLE</td>
                    <td><span class="badge bg-success">Published</span></td>
                    <td class="text-end">
                        <a href="#" class="btn btn-sm btn-outline-secondary">View</a>
                        <a href="#" class="btn btn-sm btn-outline-primary">Edit</a>
                    </td>
                </tr>
                <tr>
                    <td>customer_form</td>
                    <td>Customer Form</td>
                    <td>FORM</td>
                    <td>customer</td>
                    <td>SECTIONED</td>
                    <td><span class="badge bg-warning text-dark">Draft</span></td>
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