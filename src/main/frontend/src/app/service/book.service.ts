import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "../model/book";

@Injectable()
export class BookService {

  private static readonly  URL="http://localhost:8080/liba/books";
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
      constructor(protected httpClient: HttpClient){

      }




    public getBooks(): Observable<Array<Book>>{
        return this.httpClient.get<Array<Book>>(BookService.URL)
    }

    public getBookById(id): Observable<Book>{
        return this.httpClient.get<Book>(BookService.URL+"/"+id)
    }

    public save(book:Book): Observable<Book>{
      console.log(book.bookTitle);
      console.log(book.isbn);
      console.log(JSON.stringify(book));
     return this.httpClient.post<Book>(BookService.URL,book,{headers:this.headers});
    }
}
