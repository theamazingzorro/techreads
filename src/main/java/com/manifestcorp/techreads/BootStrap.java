package com.manifestcorp.techreads;

import com.manifestcorp.techreads.model.Book;
import com.manifestcorp.techreads.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BootStrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    protected BookRepository bookRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(bookRepository.count() < 3) {
            bookRepository.save(new Book("Beginning Groovy and Grails", "Christopher Judd", "https://th.bing.com/th/id/OIP.Z1_9wj8Wva5EWegbK76MRwAAAA?w=119&h=180&c=7&r=0&o=5&dpr=1.5&pid=1.7", 4.2));
            bookRepository.save(new Book("Pro Eclipse JS", "Azat Mardan", "https://th.bing.com/th/id/OIP.TlBwQ97UnOauwOd3bsyJeAHaJI?w=156&h=192&c=7&r=0&o=5&dpr=1.5&pid=1.7", 3.2));
            bookRepository.save(new Book("Enterprise Java Development on a Budget", "Christopher Judd", "https://i.ebayimg.com/images/g/QFEAAOSwdNFZcc8S/s-l400.jpg", 4));
        }
    }
}
