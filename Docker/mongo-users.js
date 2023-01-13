connection = new Mongo();

conn = Mongo("mongodb://localhost:27017/WhatsNew?authSource=admin");

db = db.getSiblingDB('WhatsNew');

db.mycollection.findOne();

db.createUser(
  {
    user: "whatsnewuser",
    pwd: "whatsnewpass",
    roles: [
       { role: "userAdminAnyDatabase", db: "admin" }
    ]
  }
);

db.auth('whatsnewuser', 'whatsnewpass');

db.createCollection("articles");