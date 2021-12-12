import React from 'react'
import {Form,Col,Button} from 'react-bootstrap'

class DonorRegistrationForm extends React.Component {
    constructor() {
        super()
        this.state = {
            name:"",
            age:0,
            gender:"",
            weight:0,
            bloodGroup:"",
            email:"",
            phone:"",
            pincode:"",
            city:"",
            state:"Telangana",
            covidPosDate:"",
            covidNegDate:"",
            dateOfRegistration:"",
            type:"donor"
        }
        this.handleChange = this.handleChange.bind(this)
    }

    handleChange(event) {
        const {name, value} = event.target
        this.setState({
            [name] : value
        })
    }

    validate() {
        return true;
    }

    handleSubmit() {
        if(this.validate()) {
            //process state -- cognito user creation (signup) and POST request to Lambda1 (to store details in DynamoDb Table)
        }
    }

    render() {
        return (
            <div>
                <Form>
                    <Form.Row>
                        <Form.Group as={Col} controlId="formDetailsEmail">
                            <Form.Label>Email</Form.Label>
                            <Form.Control type="email" name="email" value={this.state.email} onChange={this.handleChange} required/>
                        </Form.Group>

                        <Form.Group as={Col} controlId="formDetailsPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" name="password" required/>
                        </Form.Group>
                    </Form.Row>

                    <Form.Group controlId="formDetailsName">
                        <Form.Label>Name</Form.Label>
                        <Form.Control type="text" name="name" value={this.state.name} onChange={this.handleChange} required/>
                    </Form.Group>

                    <Form.Group controlId="formDetailsGender">
                        <Form.Label>Gender</Form.Label>
                        <br />
                            <Form.Check inline type="radio" name="gender" label="Male" value="Male" 
                                checked={this.state.gender === "Male"} onChange={this.handleChange} required />
                            <Form.Check inline type="radio" name="gender" label="Female" value="Female"
                                checked={this.state.gender === "Female"} onChange={this.handleChange} />
                            <Form.Check inline type="radio" name="gender" label="Other" value="Other"
                                checked={this.state.gender === "Other"} onChange={this.handleChange} />
                    </Form.Group>

                    <Form.Row>
                        <Form.Group as={Col} controlId="formDetailsAge">
                            <Form.Label>Age</Form.Label>
                            <Form.Control type="number" name="age" value={this.state.age} onChange={this.handleChange} required/>
                        </Form.Group>

                        <Form.Group as={Col} controlId="formDetailsWeight">
                            <Form.Label>Weight</Form.Label>
                            <Form.Control type="number" name="weight" value={this.state.weight} onChange={this.handleChange} required/>
                        </Form.Group>

                        <Form.Group as={Col} controlId="formDetailsBloodGroup">
                            <Form.Label>Blood Group</Form.Label>
                            <Form.Control as="select" name="bloodGroup" value={this.state.bloodGroup} onChange={this.handleChange} required>
                                <option style={{display:'none'}}>select</option>
                                <option value="O+">O+</option>
                                <option value="O-">O-</option>
                                <option value="A+">A+</option>
                                <option value="A-">A-</option>
                                <option value="B+">B+</option>
                                <option value="B-">B-</option>
                                <option value="AB+">AB+</option>
                                <option value="AB-">AB-</option>
                            </Form.Control>
                        </Form.Group>
                    </Form.Row>

                    <Form.Row>
                        <Form.Group as={Col} controlId="formDetailsCity">
                            <Form.Label>City</Form.Label>
                            <Form.Control as="select" name="city" value={this.state.city} onChange={this.handleChange} required>
                                <option style={{display:'none'}}>select</option>
                                <option value="Hyderabad">Hyderabad</option>
                                <option value="Warangal">Warangal</option>
                                <option value="Nalgonda">Nalgonda</option>
                            </Form.Control>
                        </Form.Group>

                        <Form.Group as={Col} controlId="formDetailsState">
                            <Form.Label>State</Form.Label>
                            <Form.Control type="text" name="state" value="Telangana" readOnly />
                        </Form.Group>

                        <Form.Group as={Col} controlId="formDetailsPincode">
                            <Form.Label>Pincode</Form.Label>
                            <Form.Control type="text" name="pincode" value={this.state.pincode} onChange={this.handleChange} required/>
                        </Form.Group>
                    </Form.Row>

                    <Form.Row>
                        <Form.Group as={Col} controlId="formDetailsPositiveDate">
                            <Form.Label>Date of Covid Positive</Form.Label>
                            <Form.Control type="date" name="covidPosDate" value={this.state.covidPosDate} onChange={this.handleChange} required/>
                        </Form.Group>

                        <Form.Group as={Col} controlId="formDetailsNegativeDate">
                            <Form.Label>Date of Covid Negative</Form.Label>
                            <Form.Control type="date" name="covidNegDate" value={this.state.covidNegDate} onChange={this.handleChange} required/>
                        </Form.Group>
                    </Form.Row>
                   
                    <Form.Group controlId="formDetailsPhone">
                        <Form.Label>Phone</Form.Label>
                        <Form.Control type="tel" name="phone" value={this.state.phone} onChange={this.handleChange} required/>
                    </Form.Group>
                    
                    <Form.Group controlId="formDetailsSubmit">
                        <Button as="input" type="submit" value="Register as Donor" onSubmit={this.handleSubmit} />
                    </Form.Group>
                    
                </Form>
            </div>
        )
    }
}

export default DonorRegistrationForm