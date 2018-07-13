# [queryChain](https://play.google.com/store/apps/details?id=com.querychain.mainapp)
This application is designed to be modular, allowing for easy deployments of retrained Tensorflow models, with the model's classes assigned to a SQLite database. This repository, specifically, uses the mobilenet_v1_1.0_224 model. There's a variety of ways this could be used (likely educational), for queryChain, it is used as an image recognition model which connects to a library of environmental and social certificates.


### Prerequisites

If you'd like to use a retrained model for this tutorial, it would be helpful for you to be familiar with *"Tensorflow for Poets 2: TFLite"* to understand how image retraining is done. For this repository, we will be using a retrained model of mobilenet_v1_1.0_224 architecture. 

*Tensorflow* is used to retrain our *mobilenet_v1_1.0_224*, and as such, you should download it. 

*Tensorflow* can be built through Bazel or acquired as a Python nightly package.

### Retraining

*This is under the assumption you have set Tensorflow up.* 
```
cd tensorflow
python tensorflow/examples/image_retraining/retrain.py   --image_dir ~/tf_files/flower_photos   --architecture mobilenet_1.0_224

```
Consider [modifying your arguments](https://github.com/tensorflow/tensorflow/blob/master/tensorflow/examples/image_retraining/retrain.py#L1297) for retraining. 
For example, if we wished to add more training steps to improve accuracy: ```--how_many_training_steps=```


After this command is finished, you should now have acquired a *output_graph.pb* and *output_labels.txt* file. By default, these are stored in your */tmp* folder.

 We'll need both files. You will now need to run the "output_graph.pb" file through *toco*. 


```toco  --input_file=/tmp/output_graph.pb  --output_file=/tmp/graph.lite --input_format=TENSORFLOW_GRAPHDEF --output_format=TFLITE --input_shape=1,224,224,3 --input_array=input --output_array=final_result --inference_type=FLOAT --inference_input_type=FLOAT```

You should now have a *graph.lite* file in */tmp*, we will need this and the *output_labels.txt* files for later.

## SQLite

Because we've retrained our Mobilenet model, we will also need to have a SQLite .db file to match. For queryChain, we've used *"DB Browser for SQLite"* as we had wished to add BLOB data. The BLOB data allows us to use images in our database, but it should be noted that this is usually bad practice for large databases. You should tailor this database to your needs, but it's absolutely neccessary for you to open your output_labels.txt file to check your classes. The class at the very top of the list should be the first entry in your database, and the very bottom class in output_labels.txt should be the last entry. 

queryChain's schema, for reference:
```
CREATE TABLE IF NOT EXISTS certs (
    id    INTEGER NOT NULL,
    name    TEXT NOT NULL,
    description    TEXT NOT NULL,
    image    TEXT NOT NULL,
    imagename    TEXT,
    PRIMARY KEY(id) 
```
If queryChain's output_labels.txt (later relabeled "labels.txt") were to look like this:
```Sustainability in Practice
Bird Friendly
Fair Trade Certified
Leaping Bunny
Green Seal
Energy Star
WaterSense
EcoLogo
Global Organic Textile Standard
Greenguard
Bio Suisse
```

We'd have *Sustainability in Practice* read as *ID: 0*, with *Bio Suisse* read as *ID: 10*.


### Replace Assets
You can now replace the default assets created for the queryChain application with your own. Rename your *output_labels.txt* to *labels.txt*. 

The *labels.txt* and *graph.lite* files should now go inside of *app/src/main/assets*. 

You should change the name of your database from the default, and you should also ensure that you've changed the DataBaseHandler.java class to match this change, which happens at line 12 or *DATABASE_NAME*.

The database you have created can now go inside of *app/src/main/assets/databases*. 


## Deployment
Assuming you've finished all of these steps, you should be able to build your app and test things out! Feel free to submit a PR.

## Authors

* *vulet* - [queryChain](https://querychain.com/)


## License

This project is licensed under the APACHE license - see the [LICENSE.md](LICENSE) file for details


----
## Example
![HomeScreen](https://raw.githubusercontent.com/queryChain/queryChain/master/example_images/EN1.png)
![SQLiteEntry](https://raw.githubusercontent.com/queryChain/queryChain/master/example_images/EN2.png)
![RecognitionScreen](https://raw.githubusercontent.com/queryChain/queryChain/master/example_images/EN3.png)
![SQLiteLibrary](https://raw.githubusercontent.com/queryChain/queryChain/master/example_images/EN4.png)
