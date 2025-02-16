import { DOCUMENT } from '@angular/common';
import { Injectable, inject, signal } from '@angular/core';

export type Theme = 'light' | 'dark';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private readonly document = inject(DOCUMENT);
  public readonly currentTheme = signal<Theme>('light');
  constructor() { 
    const theme = this.getLocalStorageTheme();
    this.setTheme(theme);
  }

  toggleTheme() {
    if(this.currentTheme() === 'light') {
      this.setTheme('dark');
      console.log('dark');
    } else {
      this.setTheme('light');
      console.log('light');
    }
  }

  setTheme(theme: Theme) {
    this.currentTheme.set(theme);
    if(theme === 'dark') {
      this.document.documentElement.classList.add('dark-mode');
    } else {
      this.document.documentElement.classList.remove('dark-mode');
      
    }
    this.setLocalStorageTheme(theme);
  }

  private setLocalStorageTheme(theme: Theme) {
    localStorage.setItem('theme', theme);
  }

  private getLocalStorageTheme(): Theme {
    return localStorage.getItem('theme') as Theme || 'light';
  }
}
