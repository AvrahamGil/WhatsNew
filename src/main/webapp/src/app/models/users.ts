export class Users {
    email:string | undefined;
    userName:string | undefined;
    password:string | undefined;
    confirmPassword:string | undefined;
    fullName:string | undefined;
    country:string | undefined;
    withImage:boolean | undefined = false;
    image:any | undefined;
    captcha!:any;
}