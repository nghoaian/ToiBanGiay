// library
import React from 'react'
import { Container, Row, Col, ListGroup, ListGroupItem} from 'reactstrap'
import { Link } from 'react-router-dom';
// logo
import logo from "../../assets/img/logo.png";
// css
import '../../assets/css/footer.css'
const Footer = () => {
  return (
    <footer className='footer'>
      <Container>
        <Row>
          
          <Col lg='3' md='4' sm='6'>
            <h5 className='footer__title'>Delivery Time</h5>
            <ListGroup className='delivery__time-list'>
              <ListGroupItem className='delivery__time-item border-0 ps-0'>
                <span>Sunday - Thursday </span>
                <p>10:00am - 11:00pm</p>
              </ListGroupItem>
              <ListGroupItem className='delivery__time-item border-0 ps-0'>
                <span>Friday - Saturday </span>
                <p>Off day</p>
              </ListGroupItem>
              
            </ListGroup>
          </Col>
          <Col lg='3' md='4' sm='6'>
          <h5 className='footer__title'>Delivery Time</h5>
            <ListGroup className='delivery__time-list'>
            <ListGroupItem className='delivery__time-item border-0 ps-0'>
                <span>Location: 19 Nguyen Huu Tho Street, Tan Phong Ward, District 7, Hcm City, VN </span>
              </ListGroupItem>
              <ListGroupItem className='delivery__time-item border-0 ps-0'>
                <span>Phone: 0987654321 </span>
              </ListGroupItem>
              <ListGroupItem className='delivery__time-item border-0 ps-0'>
                <span>Email: admin@example.com</span>
              </ListGroupItem>
              
            </ListGroup>
          </Col>
        </Row>
        <Row className='mt-5'>
      
          <Col lg='6' md='6'>
            <div className="social__links d-flex align-items-center gap-4 justify-content-end">
              <p className='mb-0'>Follow: </p>
              <span>
                <Link to='https://www.facebook.com/'><i className='ri-facebook-line'></i></Link>
              </span>
              <span>
                <Link to='https://www.github.com/'><i className='ri-github-line'></i></Link>
              </span>
              <span>
                <Link to='https://www.youtube.com/'><i className='ri-youtube-line'></i></Link>
              </span>
              <span>
                <Link to='https://www.linkedin.com/'><i className='ri-linkedin-line'></i></Link>
              </span>
            </div>
          </Col>
        </Row>
      </Container>
    </footer>
  )
}

export default Footer
