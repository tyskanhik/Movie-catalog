import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Movie } from '../models/movie.model';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private apiUrl = 'http://localhost:8080';
  movies = signal<Movie[]>([]);
  allMovies = signal<Movie[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);

  constructor(private http: HttpClient) {}

  loadAllMovies(): Observable<Movie[]> {
    this.loading.set(true);
    this.error.set(null);

    return this.http.get<Movie[]>(`${this.apiUrl}/api/movies`).pipe(
      map(movies => movies.map(movie => ({
        ...movie,
        posterUrl: `${this.apiUrl}/media/${movie.posterUrl}`
      }))),
      tap({
        next: movies => {
          this.allMovies.set(movies);
          this.movies.set(movies);
          this.loading.set(false);
        },
        error: error => {
          this.error.set(this.getErrorMessage(error));
          this.loading.set(false);
        }
      }),
      catchError(error => {
        console.error('Movie load error:', error);
        return of([]);
      })
    );
  }

  private getErrorMessage(error: any): string {
    if (error.status === 0) {
      return 'Нет соединения с сервером. Проверьте интернет-соединение.';
    } else if (error.status >= 500) {
      return 'Ошибка сервера. Пожалуйста, попробуйте позже.';
    } else {
      return 'Произошла ошибка при загрузке фильмов.';
    }
  }

  filterMovies(searchTerm: string): void {
    if (!searchTerm.trim()) {
      this.movies.set(this.allMovies());
      return;
    }

    const term = searchTerm.toLowerCase();
    const filtered = this.allMovies().filter(movie => 
      movie.title.toLowerCase().includes(term) ||
      movie.director.toLowerCase().includes(term) ||
      movie.genres.some(genre => genre.toLowerCase().includes(term))
    );
    
    this.movies.set(filtered);
  }

  resetMovies(): void {
    this.movies.set(this.allMovies());
  }
}