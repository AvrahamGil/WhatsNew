


export class ValidateService {


    speicalChar:string = "~`!#$%^&*()_+-=|\\/}]{[\/:;?/>.<,-";

    constructor(){}

    public validateDetails(type:string,value:string, anotherValue?:string) : boolean {
        if(value === null) return false;

        if(!this.validateInput(value)) return false;

        var confirmEmail = type === "email" ? value.includes("@") && value.endsWith(".com") ||  value.endsWith(".co.il") || value.endsWith(".net") || value.endsWith(".gov") || value.endsWith(".org"): false;

        if(type === "email" && confirmEmail == false) return false;

        var confirmPass = type === "password" ? this.validatePassword(value,anotherValue) : true;

        return confirmPass;
      }

      private validateInput(value:string) : boolean {
        if(value === null || value.length < 1 || value.includes(this.speicalChar)) {
          return false;
        }

        return true;
      }

      private validatePassword(value:string,anotherValue?:string) : boolean{
        if(anotherValue !== undefined) {
          var sameLength = value.length === anotherValue?.length ? true : false;

          var samePass = sameLength && value === anotherValue ? true : false;

          return samePass;
        } else if(value !== undefined && anotherValue === undefined){
            return true;
        }

        return false;
      }

    }