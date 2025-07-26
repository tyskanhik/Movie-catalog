import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MovieService } from './services/movie.services';
import { MovieCard } from "./components/movie-card/movie-card";
import { MovieModal } from "./components/movie-modal/movie-modal";

import { Loader } from "./components/loader/loader";
import { debounceTime, distinctUntilChanged, Subject, takeUntil } from 'rxjs';
import { Movie } from './models/movie.model';

@Component({
  selector: 'app-root',
  imports: [FormsModule, MovieCard, MovieModal, Loader],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected movieService = inject(MovieService);
  searchTerm = signal('');
  selectedMovieId = signal<number | null>(null);
  
  private searchTerm$ = new Subject<string>();
  private destroy$ = new Subject<void>();

  constructor() {
    this.movieService.loadAllMovies().subscribe({
      error: (err) => console.error('Failed to load movies', err)
    });
    
    this.searchTerm$
      .pipe(
        debounceTime(300),
        distinctUntilChanged(),
        takeUntil(this.destroy$)
      )
      .subscribe(term => {
        this.searchTerm.set(term);
        this.movieService.filterMovies(term);
      });
  }

  trackByMovieId(index: number, movie: Movie): number {
    return movie.id;
  }

  onSearchTermChange(term: string): void {
    this.searchTerm$.next(term);
  }

  openModal(movieId: number): void {
    this.selectedMovieId.set(movieId);
  }

  closeModal(): void {
    this.selectedMovieId.set(null);
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
