const express = require('express');

const app=express();
const bodyParser= require('body-parser');
const mongoose= require('mongoose');
const date= require(__dirname + "/date.js");

app.set('view engine', 'ejs');
app.use(bodyParser.urlencoded({extended: true}));
app.use(express.static("public"));

//connecting to mongodb
mongoose.connect("mongodb://127.0.0.1:27017/todolist" , {useNewUrlParser: true}); 
var db=mongoose.connection;
db.on('error', console.log.bind(console, "connection error"));
db.once('open', function(callback){
   console.log("connection succeeded");
})

//creating schema
const itemsschema = {
    name:String
};

const listschema = {
    name:String,
    items: [itemsschema]
};

const List = mongoose.model("List",listschema);
const Item = mongoose.model("Item",itemsschema);

const item1 = new Item({
    name:"item 1"
});
const item2 = new Item({
    name:"item 2"
});
const item3 = new Item({
    name:"item 3"
});

const defaultitems = [item1, item2, item3];




app.get("/",function(req,res){
    
    Item.find().then(function(documents){
        if(documents.length===0){
            Item.insertMany(defaultitems)
                .then(function () {
                    console.log("Successfully saved defult items to DB");
                })
                .catch(function (err) {
                    console.log(err);
            });
        }
        res.render("list", {listtitle: "Today", newlistitems:documents});
     })
    
});

app.post("/", function(req,res){
    let itemName = req.body.newitem;
    let listName = req.body.list;

    const newitem = new Item({
        name:itemName
    });

    if(listName==="Today"){
        newitem.save();
        res.redirect("/");
    }else{
        List.findOne({name:listName}).then(function(foundlist){
            foundlist.items.push(newitem);
            foundlist.save();
            res.redirect("/"+ listName);
        })
    }
    
    
}); 

app.post("/delete", function(req,res){
    const id=req.body.deletebtn;
    const listName=req.body.listname;

    if(listName==="Today"){
        Item.findByIdAndRemove(id).then(function(){
            console.log("Deleted ");
            res.redirect("/");
        }) .catch(function(err){
            console.log(err);
        })
    }else{
        List.findOneAndUpdate({name:listName},{$pull: {items: {_id:id}}}).then(function(foundlist){
            res.redirect("/"+listName);
        })
    }
    
})

app.get("/:customlist", function(req,res){
    const customlist=req.params.customlist;
    console.log(req.params.customlist);
    List.findOne({name:customlist}).then( function(foundlist){
        
            if(!foundlist){
                //creating a new list
                console.log("doesnt exist");
                const list= new List({
                    name: customlist,
                    items: defaultitems
                })
                list.save();
                res.redirect("/"+customlist)
            }
            else{
                //show existing list
                console.log("exists");
                res.render("list",{listtitle: foundlist.name, newlistitems:foundlist.items} )
            }
        
    }).catch(function(err){
        console.log(err);
    })
    
})


const port=5000
app.listen(port,console.log('server is listening at 5000!!!'))