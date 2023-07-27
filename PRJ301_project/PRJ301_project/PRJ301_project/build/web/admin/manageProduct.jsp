<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manager Product</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userManager.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <body>
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-6">
                            <h2>Manage <b>Product</b></h2>
                        </div>
                        <div class="col-sm-6">
                            <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Product</span></a>				
                        </div>
                    </div>
                </div>
                <form method="get" action="products">
                    <label for="search">Search:</label>
                    <input type="text" name="name" value="${name}">

                    <button type="submit">Search</button>
                </form>

                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>
                                <span class="custom-checkbox">
                                    <input type="checkbox" id="selectAll">
                                    <label for="selectAll"></label>
                                </span>
                            </th>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Image</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Category Name</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${products}" var="p">
                            <tr>
                                <td>
                                    <span class="custom-checkbox">
                                        <input type="checkbox" id="checkbox1" name="options[]" value="1">
                                        <label for="checkbox1"></label>
                                    </span>
                                </td>
                                <td>${p.id}</td>
                                <td>${p.name}</td>
                                <td>${p.description}</td>
                                <td><img src="${pageContext.request.contextPath}/${p.image}" width="100" ></td>
                                <td>${p.price}</td>
                                <td>${p.quantity}</td>
                                <td>${p.categoryName}</td>
                                <td>
                                    <a href="#editEmployeeModal" class="edit" data-toggle="modal"><i onclick="update(${p.id})" class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                                    <a href="#deleteEmployeeModal" class="delete" data-toggle="modal"><i onclick="setId(${p.id})" class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                                </td>
                            </tr> 
                        </c:forEach> 
                    </tbody>
                </table>
                <div class="clearfix">
                    <div class="hint-text">Showing <b>${userList.size()}</b> out of <b>${totalUsers}</b> entries</div>
                    <ul class="pagination">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item"><a href="?page=${currentPage - 1}&name=${name}" class="page-link">Previous</a></li>
                            </c:if>
                            <c:forEach begin="1" end="${totalPages}" var="page">
                                <c:choose>
                                    <c:when test="${page == currentPage}">
                                    <li class="page-item active"><a href="#" class="page-link">${page}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                    <li class="page-item"><a href="?page=${page}&name=${name}" class="page-link">${page}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${currentPage < totalPages}">
                            <li class="page-item"><a href="?page=${currentPage + 1}&name=${name}" class="page-link">Next</a></li>
                            </c:if>
                    </ul>
                </div>
            </div>
        </div>
        <!-- Create User Modal -->
        <div class="modal fade" id="addEmployeeModal" tabindex="-1" role="dialog" aria-labelledby="createUserModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createUserModalLabel">Create Product</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form method="post" action="products" enctype="multipart/form-data">
                        <input hidden="" name="action" value="create"/>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="name">Name</label>
                                <input type="text" required="" name="name" class="form-control" placeholder="Enter name">
                            </div>
                            <div class="form-group">
                                <label for="name">Description</label>
                                <input type="text" required="" name="des" class="form-control" placeholder="Enter description">
                            </div>
                            <div class="form-group">
                                <label for="name">Price</label>
                                <input type="number" required="" name="price" class="form-control" placeholder="Enter price">
                            </div>
                            <div class="form-group">
                                <label for="name">Quantity</label>
                                <input type="number" required="" name="quantity"  class="form-control" placeholder="Enter quantity">
                            </div>
                            <div class="form-group">
                                <label for="name">Image</label>
                                <input type="file" required="" name="image" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="gender">Category</label>
                                <select name="categoryId" >
                                    <c:forEach items="${lscate}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="gender">Status</label>
                                <select class="form-control" >
                                    <option selected="" value="1">Active</option>
                                    <option value="0">Inactive</option>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- Create User Modal -->
        <div class="modal fade" id="editEmployeeModal" tabindex="-1" role="dialog" aria-labelledby="createUserModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createUserModalLabel">Update Product</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form method="post" action="products" enctype="multipart/form-data">
                        <input hidden="" name="action" value="update"/>
                        <input hidden="" id="updateId" name="id" />
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="name">Name</label>
                                <input type="text" required="" id="name" name="name" class="form-control" placeholder="Enter name">
                            </div>
                            <div class="form-group">
                                <label for="name">Description</label>
                                <input type="text" required="" name="des" id="des" class="form-control" placeholder="Enter description">
                            </div>
                            <div class="form-group">
                                <label for="name">Price</label>
                                <input type="number" required="" name="price" id="price" class="form-control" placeholder="Enter price">
                            </div>
                            <div class="form-group">
                                <label for="name">Image</label>
                                <input type="file"  name="image" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="name">Quantity</label>
                                <input type="number" required="" name="quantity" id="quantity" class="form-control" placeholder="Enter quantity">
                            </div>
                            <div class="form-group">
                                <label for="gender">Category</label>
                                <select name="categoryId" id="updateCid">
                                    <c:forEach items="${lscate}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="gender">Status</label>
                                <select id="status" class="form-control" >
                                    <option selected="" value="1">Active</option>
                                    <option value="0">Inactive</option>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <!-- Delete Modal HTML -->
        <div id="deleteEmployeeModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="products" method="post">
                        <input hidden="" name="action" value="delete"/>
                        <input hidden="" name="id" id="id"/>
                        <div class="modal-header">						
                            <h4 class="modal-title">Delete Employee</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        </div>
                        <div class="modal-body">					
                            <p>Are you sure you want to delete these Records?</p>
                            <p class="text-warning"><small>This action cannot be undone.</small></p>
                        </div>
                        <div class="modal-footer">
                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                            <input type="submit" class="btn btn-danger" value="Delete">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <<script >
            const setId = (id) => {
                $('#id').val(id);
            };
            const update = async (id) => {
                const res = await (await fetch('api?action=product&id=' + id)).json();
                console.log(res);
                $('#updateId').val(id);
                $('#name').val(res.name);
                $('#des').val(res.description);
                $('#price').val(res.price);
                $('#quantity').val(res.quantity);
                $('#categoryId').val(res.categoryId);
                $('#status').val(res.status ? 1 : 0);
            };
            $(document).ready(function () {

                // Activate tooltip
                $('[data-toggle="tooltip"]').tooltip();

                // Select/Deselect checkboxes
                var checkbox = $('table tbody input[type="checkbox"]');
                $("#selectAll").click(function () {
                    if (this.checked) {
                        checkbox.each(function () {
                            this.checked = true;
                        });
                    } else {
                        checkbox.each(function () {
                            this.checked = false;
                        });
                    }
                });
                checkbox.click(function () {
                    if (!this.checked) {
                        $("#selectAll").prop("checked", false);
                    }
                });
            });
        </script>
    </body>
</html>