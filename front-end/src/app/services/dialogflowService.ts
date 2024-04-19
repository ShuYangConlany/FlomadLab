// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';

// @Injectable({
//   providedIn: 'root'
// })
// export class chatService {

//   constructor(private http: HttpClient) { }

//   initConversation() {
//     return this.http.post('/api/initiateConversation', {});
//   }
  
//   sendMessage(message: string) {
//     const url = 'https://us-central1-flomadaiplanner.cloudfunctions.net/initConversation'; // your Webhook URL
//     const body = {
//       session: 'unique-session-id', // your session ID    TODO: replace by the customer's ID
//       queryInput: {
//         text: {
//           text: message
//         },
//         languageCode: 'en'
//       }
//     };

//     return this.http.post(url, body);
//   }
// }

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class dialogflowService {
  constructor(private http: HttpClient) { }

  fetchInitResponse(): Observable<any> {
    return this.http.post('https://us-central1-flomadaiplanner.cloudfunctions.net/initConversation', {});
  }

  fetchResponse(userMessage: string): Observable<any> {
    const requestBody = { message: userMessage };
    console.log(requestBody)
    // return this.http.post(this.cloudFunctionUrl, requestBody);
    return this.http.post('https://us-central1-flomadaiplanner.cloudfunctions.net/DialogflowController', requestBody);
  }





}
