package com.example.editorial.controller;

import com.example.editorial.entity.Editorial;
import com.example.editorial.models.Book;
import com.example.editorial.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
@RestController
public class EditorialController {
    @Autowired
    private final EditorialService edService;

    public EditorialController(EditorialService edService) {
        this.edService = edService;
    }

    @GetMapping("/editoriales")
    public List<Editorial> list(){
        return edService.listAll();
    } //check

    @GetMapping("/editoriales/{id}")
    public ResponseEntity<Editorial> get(@PathVariable Integer id){
        try{
            Editorial ed= edService.get(id);
            return new ResponseEntity<Editorial>(ed, HttpStatus.OK);
        }catch(NoSuchElementException e){
            //if no product is found, it returns HTTP status Not Found (404).
            return new ResponseEntity<Editorial>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editoriales/{id}")
    public ResponseEntity<?> update(@RequestBody Editorial editorial,@PathVariable Integer id){
        try{
            Editorial existEditorial= edService.get(id);
            existEditorial.setName(editorial.getName());
            System.out.println(editorial.getName());
            existEditorial.setWeb_site(editorial.getWeb_site());
            edService.save(existEditorial);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/editoriales")
    public void add(@RequestBody Editorial ed){
        edService.save(ed);
    }

    @PatchMapping("/editoriales/{id}")
    public ResponseEntity<?> patch(@PathVariable Integer id, @RequestBody Map<String,Object> fields){
        Editorial existEditorial= edService.get(id);
        fields.forEach((key,value)->{
            Field field = ReflectionUtils.findField(Editorial.class,key);
            if(field !=null){
                field.setAccessible(true);
                ReflectionUtils.setField(field, existEditorial,value);
            }
        });
        Editorial result = edService.save(existEditorial);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/editoriales/{id}")
    public void delete(@PathVariable Integer id){
        edService.delete(id);
    }

    @GetMapping("/editoriales/books/{editorialId}")
    public ResponseEntity<List<Book>> getBooks(@PathVariable("editorialId") Integer id){
        Editorial ed = edService.get(id);
        if(ed == null){
            return ResponseEntity.notFound().build();
        }
        List<Book> books = edService.getBooks(id);
        return ResponseEntity.ok(books);

    }
}
