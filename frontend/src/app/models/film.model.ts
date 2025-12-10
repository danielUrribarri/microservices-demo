export type MpaaRating = 'G' | 'PG' | 'PG-13' | 'R' | 'NC-17';

export interface Film {
  filmId: number;
  title: string;
  description?: string;
  releaseYear?: number;
  languageId: number;
  originalLanguageId?: number | null;
  rentalDuration: number;
  rentalRate: number;
  length?: number;
  replacementCost: number;
  rating?: MpaaRating | null;
  specialFeatures?: string[];
}

export interface FilmRequest {
  title: string;
  description?: string;
  releaseYear?: number;
  languageId: number;
  originalLanguageId?: number | null;
  rentalDuration: number;
  rentalRate: number;
  length?: number;
  replacementCost: number;
  rating?: MpaaRating | null;
  specialFeatures?: string[];
}
