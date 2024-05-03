import os
import numpy as np
from PIL import Image
import tensorflow as tf

class DeepLabModel(object):
    """Class to load deeplab model and run inference."""
    INPUT_TENSOR_NAME = 'ImageTensor:0'
    OUTPUT_TENSOR_NAME = 'SemanticPredictions:0'
    INPUT_SIZE = 513

    def __init__(self, model_dir):
        """Initialize the model with a directory containing the frozen graph."""
        self.graph = tf.Graph()
        graph_path = os.path.join(model_dir, 'frozen_inference_graph.pb')  # Adjust this if the name differs
        with self.graph.as_default():
            with tf.io.gfile.GFile(graph_path, 'rb') as f:
                graph_def = tf.compat.v1.GraphDef()
                graph_def.ParseFromString(f.read())
                tf.import_graph_def(graph_def, name='')

        self.sess = tf.compat.v1.Session(graph=self.graph)

    def run(self, image):
        """Runs inference on a single image."""
        width, height = image.size
        resize_ratio = 1.0 * self.INPUT_SIZE / max(width, height)
        target_size = (int(resize_ratio * width), int(resize_ratio * height))
        resized_image = image.convert('RGB').resize(target_size, Image.ANTIALIAS)
        batch_seg_map = self.sess.run(
            self.OUTPUT_TENSOR_NAME,
            feed_dict={self.INPUT_TENSOR_NAME: [np.asarray(resized_image)]})
        seg_map = batch_seg_map[0]
        return resized_image, seg_map

# Specify the directory where the model's frozen graph is stored
model_dir = '/content/drive/MyDrive/deeplabv3_pascal_trainval/'  # Adjust this to your model's directory
model = DeepLabModel(model_dir)

# Specify the path to your image
image_path = '/content/Picture 7.PNG'
image = Image.open(image_path)
resized_image, seg_map = model.run(image)

# Optionally save or display the results
resized_image.save('/content/resized_image.jpg')
seg_map_image = Image.fromarray(seg_map.astype('uint8'))
seg_map_image.save('/content/segmentation_map.jpg')

