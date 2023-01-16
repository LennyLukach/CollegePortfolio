import pygame
import sys
from pygame import mouse
from pygame.constants import QUIT


pygame.init()

SCREEN = pygame.display.set_mode((1280, 800))
pygame.display.set_caption("Stones Game")
RED = (255, 0, 0)

while True:
    for event in pygame.event.get():
        mouse_pos_x, mouse_pos_y = pygame.mouse.get_pos()
        pygame.draw.ellipse(SCREEN, RED, (mouse_pos_x, mouse_pos_y, 5, 5))
        pygame.display.update()
        if event.type == QUIT:
            pygame.quit()
            sys.exit()