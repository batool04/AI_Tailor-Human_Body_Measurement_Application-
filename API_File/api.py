from flask import Flask, request, jsonify
from werkzeug.utils import secure_filename
import os
from PIL import Image
import cv2
from DeepLabModel import DeepLabModel
from demo import main
import numpy as np

# from inference import performInference

UPLOAD_FOLDER = './uploads'


app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route('/hello/', methods=['GET', 'POST'])
def welcome():
    if request.method == 'POST':
        
        print(request.form['height'])

        return 'post called'

    return "Hello World!"


@app.route('/predict/', methods=['GET', 'POST'])
def predict():
    if request.method == 'POST':
        image = request.files['input_image']
        
        
        if 'height' in request.form:
            height = request.form['height']
        else:
             height = 160 # or some other default

        filename = secure_filename(image.filename)
        inputImagePath = os.path.join(app.config['UPLOAD_FOLDER'], filename)
        image.save(inputImagePath)

        # performing inference
        image = Image.open(inputImagePath)
        
        #print("Image Type = ",type(image))
        back = cv2.imread('sample_data/input/background.jpeg',cv2.IMREAD_COLOR)

        model_path = os.path.join('deeplab_model', 'deeplabv3_pascal_trainval_2018_01_04.tar.gz')
        
        MODEL = DeepLabModel(model_path)

        res_im, seg = MODEL.run(image)
        seg = cv2.resize(seg.astype(np.uint8),image.size)
        mask_sel = (seg==15).astype(np.float32)
        mask = 255*mask_sel.astype(np.uint8)

        img = 	np.array(image)
        img = cv2.cvtColor(img, cv2.COLOR_RGB2BGR)   

        res = cv2.bitwise_and(img,img,mask = mask)
        bg_removed = res + (255 - cv2.cvtColor(mask, cv2.COLOR_GRAY2BGR)) 
        
        measurements = main(bg_removed, height, None)
        print( measurements)
        
        return jsonify(measurements)
    else:
        return 'Please upload an image to proceed'

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=3000)
    
