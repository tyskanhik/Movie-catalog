export interface Movie {
  id: number;
  title: string;
  year: number;
  director: string;
  plot: string;
  posterUrl: string;
  genres: string[];
  rating: number;
  duration: string;
  ageRating: string
}