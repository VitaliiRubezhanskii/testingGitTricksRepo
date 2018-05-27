import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "../model/book";

@Injectable()
export class BookService {

  private readonly  URL="http://localhost:8080/liba/books";
      constructor(protected httpClient: HttpClient){

      }

    public getBooks(): Observable<Array<Book>>{
        return this.httpClient.get<Array<Book>>(this.URL)
    }


}
