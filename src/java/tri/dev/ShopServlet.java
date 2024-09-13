package tri.dev;

import tri.dev.data.dao.DatabaseDao;
import tri.dev.data.dao.ProductDao;
import tri.dev.data.model.Product;
import tri.dev.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import tri.dev.data.dao.CategoryDao;
import tri.dev.data.model.Category;

public class ShopServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDao productDao = DatabaseDao.getInstance().getProductDao();

        // Lấy tổng số sản phẩm
        int totalProducts = productDao.countAll(); // Thêm phương thức countAll() trong ProductDao
        int page = 1;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                // Giữ mặc định là 1 nếu tham số không hợp lệ
            }
        }

        int numberPage = (int) Math.ceil((double) totalProducts / Constants.PER_PAGE);
        List<Product> productList = productDao.getProducts((page - 1) * Constants.PER_PAGE, Constants.PER_PAGE);

        // Đặt các thuộc tính cần thiết để hiển thị trên giao diện
        request.setAttribute("page", page);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("numberPage", numberPage);
        request.setAttribute("productList", productList);

        // Truy vấn danh sách danh mục từ database
        CategoryDao categoryDao = DatabaseDao.getInstance().getCategoryDao();
        List<Category> categoryList = categoryDao.findAll();
        request.setAttribute("categoryList", categoryList);

        // Forward đến trang shop.jsp
        request.getRequestDispatcher("shop.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
