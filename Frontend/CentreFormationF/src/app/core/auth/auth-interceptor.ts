import { HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const token = localStorage.getItem('token');

  let request = req;

  if (token) {
    request = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(request).pipe(
    catchError((error) => {

      console.log('HTTP ERROR :', error.status, error.url);

      if (error.status === 401) {
        console.log('ERREUR 401 - TOKEN :', token);
      }

      return throwError(() => error);
    })
  );
};