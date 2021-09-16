package com.es.phoneshop.model.productDao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.productSortEnums.SortField;
import com.es.phoneshop.model.productSortEnums.SortOrder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static com.es.phoneshop.model.demoData.DemoDataForProductDaoCreator.fillProductDaoDemoData;
import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private static ProductDao productDao;

    @BeforeClass
    public static void setup() {
        productDao = ArrayListProductDao.getInstance();
        fillProductDaoDemoData(productDao);
    }

    @Test
    public void findProducts_returnProperEmptyQuery() {

        List<Product> testProducts = productDao.findProducts("", null, null);
        boolean haveNotProper = testProducts.stream()
                .anyMatch(it ->
                        it.getPrice() == null
                                || it.getStock() <= 0);

        assertFalse(haveNotProper);
    }

    @Test
    public void findProducts_returnProperWithQuery() {

        String query = "Samsung S";

        List<Product> testProducts = productDao.findProducts(query, null, null);
        boolean haveNotProper = testProducts.stream()
                .anyMatch(it ->
                        it.getPrice() == null || it.getStock() <= 0);

        boolean isFoundedWithMatching = testProducts.stream()
                .allMatch(it ->
                        ProductDaoFindProductsUtils.areStringsContainsCommonWords(it.getDescription(), "Samsung S"));

        assertFalse(haveNotProper);
        assertTrue(isFoundedWithMatching);
        assertTrue(testProducts.size() != 0);
    }

    @Test
    public void findProducts_returnProperNullQuery() {

        List<Product> testProducts = productDao.findProducts(null, null, null);
        assertTrue(testProducts.size() != 0);
    }

    @Test
    public void findProducts_returnWithSortDescription() {

        List<Product> testProducts = productDao.findProducts("", SortField.DESCRIPTION, SortOrder.ASC);

        boolean orderIsRight = true;
        Product prev = testProducts.get(0);
        for (int i = 1; i < testProducts.size(); ++i) {
            if (prev.getDescription().compareTo(testProducts.get(i).getDescription()) > 0) {
                orderIsRight = false;
                break;
            }
        }

        assertTrue(orderIsRight);
    }

    @Test
    public void findProducts_returnWithQuerySortPrice() {

        String query = "Samsung S";

        List<Product> testProducts = productDao.findProducts(query, SortField.PRICE, SortOrder.ASC);

        boolean orderIsRight = true;
        Product prev = testProducts.get(0);
        for (int i = 1; i < testProducts.size(); ++i) {
            if (prev.getPrice().compareTo(testProducts.get(i).getPrice()) > 0) {
                orderIsRight = false;
                break;
            }
        }

        assertTrue(orderIsRight);
        assertTrue(testProducts.size() < productDao.findProducts(null, null, null).size());
    }

    @Test
    public void get_Product() {
        assertNotNull(productDao.getProduct(1L));
    }

    @Test
    public void get_ProductNotExistingId() {
        assertNull(productDao.getProduct(-1L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_ProductNullId() {
        assertNotNull(productDao.getProduct(null));
    }

    @Test
    public void save_ProductNullId() {

        Currency usd = Currency.getInstance("USD");
        Product testProduct = new Product("sgs987", "Samsung Galaxy S II iphone", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());

        boolean isAdded = productDao.save(testProduct);

        assertTrue(isAdded);
        assertTrue(testProduct.getId() > 0);
        assertSame(productDao.getProduct(testProduct.getId()), testProduct);
    }

    @Test
    public void save_ProductExistingId() {

        Currency usd = Currency.getInstance("USD");
        Product testProduct = new Product("sgs654", "Samsung Galaxy S III Apple", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());
        testProduct.setId(1L);

        boolean isAdded = productDao.save(testProduct);

        assertFalse(isAdded);
        assertEquals(productDao.getProduct(testProduct.getId()), testProduct);
        assertNotSame(productDao.getProduct(testProduct.getId()), testProduct);
    }

    @Test
    public void save_ProductNotExistingId() {

        Currency usd = Currency.getInstance("USD");
        Product testProduct = new Product("sgs654", "Galaxy S Apple", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());
        Long id = 1000L;
        testProduct.setId(id);

        boolean isAdded = productDao.save(testProduct);

        assertTrue(isAdded);
        assertTrue(testProduct.getId() > 0);
        assertNotSame(id, testProduct.getId());
        assertSame(productDao.getProduct(testProduct.getId()), testProduct);
    }

    @Test
    public void delete_Product() {
        Long id = 1L;

        assertTrue(productDao.delete(id));
        assertNull(productDao.getProduct(id));
    }

    @Test
    public void delete_ProductNotExistingId() {
        Long id = 1000L;
        assertFalse(productDao.delete(id));
    }

    @Test
    public void getInstance_returnOneInstance() {
        assertSame(productDao, ArrayListProductDao.getInstance());
    }
}
