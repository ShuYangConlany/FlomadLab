import { Injectable } from '@angular/core';
import { ReferenceDataService } from './ReferenceDataService';
import { forkJoin, lastValueFrom } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class FlightOfferReqService {
  flightOffers: any;
  errorMessage: string | undefined;

  // constructor() { }
  constructor(private referenceDataService: ReferenceDataService) { }

  convertCityNameToAirportCode(cityName: string): string {
    const airportCodes: {[key: string]: string} = {
      'San Francisco': 'SFO',
      'Boston': 'BOS',
    };
    return airportCodes[cityName] || 'SFO'; 
  }

  

  async parseApiData(apiData: string) {
    const formData = {
      originLocationCode: '',
      destinationLocationCode: '',
      departureDate: '',
      returnDate: '',
      adults: 1,
      max: 1
    };
    const regex = /Departure Location:(.*),Destination:(.*),Departure Time:(.*),Return Time:(.*),Number of People:(.*)/;
    let m: RegExpExecArray | null;

    if ((m = regex.exec(apiData)) !== null) {
        // The result can be accessed through the `m`-variable.
        m.forEach((match, groupIndex) => {
            console.log(`Found match, group ${groupIndex}: ${match}`);
        });
    }
    const matches = apiData.match(regex);
    console.log("matches:",matches)

    if (matches && matches.length >= 5) {
      const extractedData = [matches[1], matches[2], matches[3], matches[4], matches[5]];
      console.log(extractedData[0])
      console.log(extractedData[1])
      // await this.referenceDataService.getReferenceData(extractedData[0]).subscribe({
      //   next: (iataCode) => {
      //     if (iataCode) {
      //       console.log("FlightOfferReqService have originLocationCode")
      //       formData.originLocationCode = iataCode;
      //     } else {
      //       this.errorMessage = 'No data found or invalid response structure';
      //       console.log(this.errorMessage)
      //     }
      //   },
      //   error: (error) => {
      //     console.error('There was an error!', error);
      //     this.errorMessage = 'An error occurred';
      //   }
      // });
      // await this.referenceDataService.getReferenceData(extractedData[1]).subscribe({
      //   next: (iataCode) => {
      //     if (iataCode) {
      //       console.log("FlightOfferReqService have destinationLocationCode")
      //       formData.destinationLocationCode = iataCode;
      //     } else {
      //       this.errorMessage = 'No data found or invalid response structure';
      //       console.log(this.errorMessage)
      //     }
      //   },
      //   error: (error) => {
      //     console.error('There was an error!', error);
      //     this.errorMessage = 'An error occurred';
      //   }
      // });
      try {
        const [originIataCode, destinationIataCode] = await lastValueFrom(forkJoin([
          this.referenceDataService.getReferenceData(extractedData[0]),
          this.referenceDataService.getReferenceData(extractedData[1])
        ]));

        formData.originLocationCode = originIataCode || '';
        formData.destinationLocationCode = destinationIataCode || '';
        formData.departureDate = extractedData[2];
        formData.returnDate = extractedData[3];
        formData.adults = parseInt(extractedData[4], 10);
      } catch (error) {
        console.error('There was an error!', error);
        this.errorMessage = 'An error occurred';
      }
      // formData.originLocationCode = this.convertCityNameToAirportCode(extractedData[0]);
      // formData.destinationLocationCode = this.convertCityNameToAirportCode(extractedData[1]);
      console.log("FlightOfferReqService have formData.originLocationCode:",formData.originLocationCode)
      console.log("FlightOfferReqService have formData.destinationLocationCode:",formData.destinationLocationCode)

      formData.departureDate = extractedData[2];
      formData.returnDate = extractedData[3];
      formData.adults = parseInt(extractedData[4], 10);
    }
    return formData;
  }

}

