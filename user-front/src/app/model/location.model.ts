export class LocationInfo {

    locationName: string;
    description: string;
    address: string;
    city: string;
    state: string; 
    country: string;
    zipCode: string;

    constructor(locationName: string, description: string, address: string, city: string, state: string, country: string, zipCode: string) {
        this.locationName = locationName;
        this.description = description;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
      }
    
}