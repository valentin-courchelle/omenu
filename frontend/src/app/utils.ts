import { Observable } from 'rxjs';

export function parseBlobToObservableJson<T>(blob: Blob): Observable<T> {
  return new Observable((observer) => {
    const reader = new FileReader();
    reader.onload = () => {
      observer.next(JSON.parse(reader.result as string));
      observer.complete();
    };
    reader.onerror = (err) => observer.error(err);
    reader.readAsText(blob);
  });
}