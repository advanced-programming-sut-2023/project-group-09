from captcha.image import ImageCaptcha
import random

n = random.choice(range(1000, 10000))
image = ImageCaptcha()
data = image.generate(str(n))
image.write(str(n), "files/captcha/images/"+str(n) + '.png')
print(str(n) + '.png')
