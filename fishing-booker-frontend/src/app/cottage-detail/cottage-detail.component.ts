import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../login/login.service';
import { CottageDetailService } from './cottage-detail.service';

@Component({
  selector: 'app-cottage-detail',
  templateUrl: './cottage-detail.component.html',
  styleUrls: ['./cottage-detail.component.css']
})
export class CottageDetailComponent implements OnInit {

  cottage: any
  errorMessage = '';

  constructor(public readonly loginService: LoginService,
    private route: ActivatedRoute, 
    private router: Router, 
    private cottageDetailService: CottageDetailService) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.getCottage(id);
    }
  }

  getCottage(id: number): void {
    this.cottageDetailService.getCottage(id).subscribe({
      next: cottage => this.cottage = cottage,
      error: err => this.errorMessage = err
    })
  }

  public delete(id:number):void {
    this.cottageDetailService.delete(id).subscribe(
      response => {this.router.navigate(['cottages']); }
      );
    return;
  }
}
