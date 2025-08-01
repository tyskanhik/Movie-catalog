import { Component, effect, EventEmitter, HostListener, inject, Input, Output, signal } from '@angular/core';
import { MovieService } from '../../services/movie.services';
import { Movie } from '../../models/movie.model';

@Component({
  selector: 'app-movie-modal',
  imports: [],
  templateUrl: './movie-modal.html',
  styleUrl: './movie-modal.scss'
})
export class MovieModal {
  private movieService = inject(MovieService);
  @Input({ required: true }) set movieId(id: number) {
    this.loadMovie(id);
  }
  @Output() close = new EventEmitter<void>();
  movie = signal<Movie | null>(null);
  loading = signal(false);

  loadMovie(id: number): void {
    this.loading.set(true);
    const foundMovie = this.movieService.allMovies().find(m => m.id === id);
    
    if (foundMovie) {
      this.movie.set(foundMovie);
      this.loading.set(false);
    } else {
      this.movie.set(null);
      this.loading.set(false);
      console.warn(`Movie with id ${id} not found`);
    }
  }

  onClose(): void {
    this.close.emit();
  }

  @HostListener('document:keydown.escape')
  onEscapePress() {
    this.onClose();
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }
}
