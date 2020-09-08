import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {
  @Output() output: EventEmitter<any> = new EventEmitter();
  constructor(
    public dialogRef: MatDialogRef<ModalComponent>,
    @Inject(MAT_DIALOG_DATA) public modalData: any,
  ) { }

  ngOnInit() {
  }

  actionFunction() {
    // alert('I am a work in progress');
    this.output.emit(true);
    this.closeModal();
  }

  closeModal() {
    this.dialogRef.close();
  }
}
