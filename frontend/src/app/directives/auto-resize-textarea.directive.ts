import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: 'textarea[autoResize]',
  standalone: true, // Très important en mode standalone !
})
export class AutoResizeTextareaDirective {
  @Input() maxHeight = 200;

  constructor(private elementRef: ElementRef<HTMLTextAreaElement>) {}

  @HostListener('input')
  onInput(): void {
    const textarea = this.elementRef.nativeElement;
    textarea.style.height = 'auto';
    const newHeight = Math.min(textarea.scrollHeight, this.maxHeight);
    textarea.style.height = newHeight + 'px';
    textarea.style.overflowY = (textarea.scrollHeight > this.maxHeight) ? 'auto' : 'hidden';
  }

  ngAfterViewInit(): void {
    this.onInput();
  }
}
