package com.rmc.controller;

import com.rmc.pojo.Books;
import com.rmc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    //controller调service
    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;

    //查询全部的书籍，并且返回到一个数据展示页面
    @RequestMapping("/allBook")
    public String list(Model model){
        List<Books> list = bookService.queryAllBook();
        model.addAttribute("list",list);
        return "allBook";
    }

    //跳转到增加书籍页面
    @RequestMapping("/toAddBook")
    public String toAddBook(){
        return "addBook";
    }

    //添加书籍的请求
    @RequestMapping("/addBook")
    public String addBook(Books book){
        System.out.println("addBook:" + book);
        bookService.addBook(book);
        return "redirect:/book/allBook";//重定向到@RequestMapping("/allBook")请求：
    }

    //跳转到修改页面
    @RequestMapping("/toUpdateBook")
    public String toUpdateBook(int id,Model model){
        Books book = bookService.queryBookById(id);
        model.addAttribute("queryBook",book);
        return "updateBook";
    }

    //修改书籍
    @RequestMapping("/updateBook")
    public String updateBook(Books book){
        bookService.updateBook(book);
        return "redirect:/book/allBook";
    }

    //删除书籍
    @RequestMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable("bookId") int id){
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }

    //查询书籍
    @RequestMapping("/queryBook")
    public String queryBook(String queryBookName,Model model){
        List<Books> list = bookService.queryBookByName(queryBookName);

        if (list.size()==0){
            model.addAttribute("error","未查到");
        }

        model.addAttribute("list",list);
        return "allBook";
    }




}
