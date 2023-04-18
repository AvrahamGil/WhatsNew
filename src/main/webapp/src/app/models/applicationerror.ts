export class ApplicationError extends Error {

    httpStatus?: number = 400;
    applicationStatus?: number;
    errorMessageTranslationkey?: string;
    handled: boolean = false;

    constructor(message?: string) {
      super(message);
      this.errorMessageTranslationkey = message;
      this.name = ApplicationError.name;
    }
  }