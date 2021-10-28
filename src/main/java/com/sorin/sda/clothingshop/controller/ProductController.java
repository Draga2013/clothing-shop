package com.sorin.sda.clothingshop.controller;

import com.sorin.sda.clothingshop.model.Account;
import com.sorin.sda.clothingshop.model.OrderLine;
import com.sorin.sda.clothingshop.model.Product;
import com.sorin.sda.clothingshop.model.Size;
import com.sorin.sda.clothingshop.repository.AccountRepository;
import com.sorin.sda.clothingshop.repository.OrderLineRepository;
import com.sorin.sda.clothingshop.repository.ProductRepository;
import com.sorin.sda.clothingshop.service.AccountService;
import com.sorin.sda.clothingshop.service.CategoryService;
import com.sorin.sda.clothingshop.service.ProductService;
import com.sorin.sda.clothingshop.service.dto.CartItemDTO;
import com.sorin.sda.clothingshop.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final AccountService accountService;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;

    public ProductController(ProductService productService, CategoryService categoryService, AccountService accountService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    @GetMapping("/product-list")
    public String showProductListPage(Model model) {
        model.addAttribute("products", productRepository.findAllBySize_Id(1L));
        return "productList";
    }

    @GetMapping("/product-list/{categoryName}")
    public String showProductListPage(Model model, @PathVariable String categoryName) {
        model.addAttribute("products", productService.findProductsByCategoryName(categoryName));
        return "index";
    }

    @GetMapping("/add-product")
    public String addProduct(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", new ProductDTO());
        return "product-add";
    }

    @PostMapping("/addproduct")
    public String saveProduct(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-add";
        }
        productService.save(productDTO);
        return "redirect:/product-list";
    }

    @GetMapping("/show-cart/{id}")
    public ModelAndView showCartPageForProduct(@PathVariable Long id) {
        Optional<User> currentUser = accountService.getLoggedInUser();
        Account account = accountRepository.findByEmail(currentUser.get().getUsername());
        ModelAndView modelAndView = new ModelAndView("cart");
        Product product = productRepository.findById(id).get();
        modelAndView.addObject("product", product);
        OrderLine orderLine = new OrderLine();
        orderLine.setAccount(account);
        orderLine.setProduct(product);
        orderLineRepository.save(orderLine);
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setProductId(product.getId());
        modelAndView.addObject("cartItem", cartItemDTO);
        return modelAndView;
    }

    @GetMapping("/show-cart")
    public ModelAndView showCartPage() {
        Optional<User> currentUser = accountService.getLoggedInUser();
        Account account = accountRepository.findByEmail(currentUser.get().getUsername());
        ModelAndView modelAndView = new ModelAndView("cart");
        Product product = new Product();
        if (orderLineRepository.findByAccount_Email(account.getEmail()) != null) {
            product = orderLineRepository.findByAccount_Email(account.getEmail()).getProduct();
            modelAndView.addObject("product", product);
            CartItemDTO cartItemModel = new CartItemDTO();
            cartItemModel.setProductId(orderLineRepository.findByAccount_Email(account.getEmail()).getProduct().getId());
            modelAndView.addObject("cartItem", cartItemModel);
        }
        else {
            modelAndView = new ModelAndView("redirect:/");
        }
        return modelAndView;
    }

    @PostMapping("/cart/add")
    public ModelAndView addToCart(@ModelAttribute("orderLine") CartItemDTO cartItemDTO) {
        Optional<User> currentUser = accountService.getLoggedInUser();
        Account account = accountRepository.findByEmail(currentUser.get().getUsername());
        OrderLine orderLine = orderLineRepository.findByAccount_Email(account.getEmail());
        Product product = productRepository.findByIdAndSize_Size(cartItemDTO.getProductId(), cartItemDTO.getSize());
        orderLine.setProduct(product);
        if (product.getQuantity() - cartItemDTO.getQuantity() >=0 ) {
            product.setQuantity(product.getQuantity() - cartItemDTO.getQuantity());
            productRepository.save(product);
            orderLineRepository.delete(orderLine);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }

    @GetMapping("/order")
    public String orderProduct(Model model) {
        model.addAttribute("products", productService.getAll());
        return "index";
    }

    @GetMapping("/edit-product/{id}")
    public String editProduct(@PathVariable("id") Long productId, Model model) {
        ProductDTO productDTO = productService.findProductDTObyId(productId);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", productDTO);
        return "product-edit";
    }

    @PostMapping("/editproduct")
    public String editProduct(@ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-edit";
        }
        productService.update(productDTO);
        return "redirect:/product-list";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable("id") Long productId) {
        productService.delete(productId);
        return "redirect:/product-list";
    }
}
