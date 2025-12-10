export interface PaginatedResponse<T> {
  page: any;
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
